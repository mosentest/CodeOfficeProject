var socket;
var registered = false;

function startClient() {
	console.log("opening socket");
	
	socket = new SockJS("http://" + document.domain + ":8080/enterprise/messaging");

	
	console.log("http://" + document.domain + ":8080/enterprise/messaging");
	socket.onopen = function() {
		console.log("Opened socket.");
		var nickname = "hahahaha";
		socket.send(nickname);
	};

	socket.onmessage = function(a) {
		console.log("received message: " + a.data);
	};
	socket.onclose = function() {
		document.write("Closed socket.");
	};
	socket.onerror = function() {
		document.write("Error during transfer.");
	};
	sendMessage();

}

function sendMessage() {
	socket.send("connected");
}