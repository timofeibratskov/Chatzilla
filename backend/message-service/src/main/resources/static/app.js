let stompClient = null;
let currentJwt = null;

$(document).ready(function() {
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#send").click(sendMessage);
    $("#updateBtn").click(updateMessage);
    $("#deleteBtn").click(deleteMessage);
});

function connect() {
    currentJwt = $('#jwtToken').val();
    const chatId = $('#chatId').val();

    if (!currentJwt || !chatId) {
        alert("Enter JWT and Chat ID");
        return;
    }

    stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8082/ws',
        connectHeaders: {
            Authorization: 'Bearer ' + currentJwt
        },
        debug: function(str) {
            console.log('STOMP: ' + str);
        }
    });

    stompClient.onConnect = (frame) => {
        console.log("Connected:", frame);
        $("#connect").prop("disabled", true);
        $("#disconnect").prop("disabled", false);

        const chatId = $('#chatId').val();

        stompClient.subscribe(`/topic/chat/${chatId}`, (message) => {
            console.log("New message:", message.body);
            showMessage(JSON.parse(message.body));
        });

        stompClient.subscribe(`/topic/chat/${chatId}/update`, (message) => {
            console.log("Message updated:", message.body);
            const msg = JSON.parse(message.body);
            $(`#msg-${msg.id} .text`).text(msg.text).addClass("text-warning");
        });

        stompClient.subscribe(`/topic/chat/${chatId}/delete`, (message) => {
            console.log("Message deleted:", message.body);
            $(`#msg-${message.body}`).fadeOut(() => $(this).remove());
        });
    };

    stompClient.onStompError = (frame) => {
        console.error("STOMP error:", frame.headers.message, frame.body);
        alert("STOMP error: " + frame.headers.message);
    };

    stompClient.activate();
}

function sendMessage() {
    if (!stompClient?.connected) {
        alert("Not connected");
        return;
    }

    const text = $("#message").val();
    if (!text) return;

    const destination = `/app/chat/${$('#chatId').val()}/message`;
    console.log("Sending to", destination, "with text:", text);

    stompClient.publish({
        destination: destination,
        body: JSON.stringify({ text: text }),
        headers: {
            'Authorization': 'Bearer ' + currentJwt
        }
    });

    $("#message").val("");
}

function updateMessage() {
    if (!stompClient?.connected) {
        alert("Not connected");
        return;
    }

    const msgId = $("#updateMsgId").val();
    const newText = $("#updateMsgText").val();
    if (!msgId || !newText) {
        alert("Enter message ID and new text");
        return;
    }

    const destination = `/app/chat/${$('#chatId').val()}/message/${msgId}/update`;
    console.log("Updating message", msgId, "via", destination);

    stompClient.publish({
        destination: destination,
        body: JSON.stringify({ text: newText }),
        headers: {
            'Authorization': 'Bearer ' + currentJwt,
            'content-type': 'application/json'
        }
    });

    $("#updateMsgId").val("");
    $("#updateMsgText").val("");
}

function deleteMessage() {
    if (!stompClient?.connected) {
        alert("Not connected");
        return;
    }

    const msgId = $("#deleteMsgId").val();
    if (!msgId) {
        alert("Enter message ID");
        return;
    }

    const destination = `/app/chat/${$('#chatId').val()}/message/${msgId}/delete`;
    console.log("Deleting message", msgId, "via", destination);

    stompClient.publish({
        destination: destination,
        headers: {
            'Authorization': 'Bearer ' + currentJwt
        }
    });

    $("#deleteMsgId").val("");
}

function showMessage(message) {
    $("#messages").prepend(`
        <tr id="msg-${message.id}">
            <td>
                <strong>${message.senderId}:</strong> 
                <span class="text">${message.text}</span>
                <div class="message-id">ID: ${message.id}</div>
            </td>
        </tr>
    `);
}