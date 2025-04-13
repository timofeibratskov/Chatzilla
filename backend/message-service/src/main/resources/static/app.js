let stompClient = null;

$(document).ready(function () {
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#send").click(sendMessage);
});

function connect() {
    const jwtToken = $('#jwtToken').val();
    const chatId = $('#chatId').val();

    if (!jwtToken || !chatId) {
        alert("Please enter JWT token and Chat ID");
        return;
    }

    stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8082/ws',
        connectHeaders: {
            Authorization: 'Bearer ' + jwtToken
        }
    });

    stompClient.onConnect = (frame) => {
        console.log('Connected to chat:', chatId);
        $("#connect").prop("disabled", true);
        $("#disconnect").prop("disabled", false);
        $("#send").prop("disabled", false);
        stompClient.subscribe(`/topic/chat/${chatId}`, (message) => {
            const msg = JSON.parse(message.body);
            showMessage(msg);
        });
    };

    stompClient.onDisconnect = () => {
        console.log('Disconnected');
        $("#connect").prop("disabled", false);
        $("#disconnect").prop("disabled", true);
        $("#send").prop("disabled", true);
    };

    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    stompClient.activate();
}

function disconnect() {
    if (stompClient) {
        stompClient.deactivate();
    }
    $("#connect").prop("disabled", false);
    $("#disconnect").prop("disabled", true);
    $("#send").prop("disabled", true);
    console.log("Disconnected");
}

function sendMessage() {
    const chatId = $('#chatId').val();
    const text = $("#message").val();
    const jwtToken = $('#jwtToken').val();

    if (!stompClient || !stompClient.connected) {
        alert('Not connected to WebSocket!');
        return;
    }

    console.log('🚀 Sending to chat', chatId, ':', text);

    stompClient.publish({
        destination: `/app/chat/${chatId}/message`,
        body: JSON.stringify({ text: text }),
        headers: {
            'content-type': 'application/json',
            'Authorization': 'Bearer ' + jwtToken // Добавляем токен
        }
    });

    $("#message").val("");
}

function showMessage(message) {
    $("#greetings").prepend(
        `<tr><td><strong>${message.senderId}:</strong> ${message.text}</td></tr>`
    );
}