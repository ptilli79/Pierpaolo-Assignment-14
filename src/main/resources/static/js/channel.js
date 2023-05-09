$(document).ready(function () {
 
  const channelId = getChannelIdFromUrl();
  const userId = getParameterByName("userId");
  const userName = getUsernameFromSessionStorage(userId);
  console.log(channelId, userId, userName);

  if (!userId) {
    window.location.href = "/";
  }

  // Display the user name
  if (userName) {
    $("#userNameDisplay").text(`User: ${userName}`);
  } else {
    // Handle the case when the user name is not available in sessionStorage
    // Redirect to the welcome page or show an error message
  }

  loadMessages();

  $("#message").keypress(function (e) {
    if (e.which === 13) {
      e.preventDefault();
      const messageText = $("#message").val();
      if (messageText) {
        createMessage(channelId, userId, messageText);
      }
    }
  });

  setInterval(loadMessages, 500);
});

function getChannelIdFromUrl() {
  const urlParts = window.location.pathname.split("/");
  return urlParts[urlParts.length - 1];
}

function loadMessages() {
  const channelId = getChannelIdFromUrl();
  const userId = getParameterByName("userId");
  const userName = getUsernameFromSessionStorage(userId);

  $.get(`/channel/${channelId}/messages`, function (messages) {
    $("#messageList").empty();
    messages.sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp));
    messages.forEach(function (message) {
      const user = message.user;
      const messageDiv = $("<div>");
      const messageTimestamp = formatTimestamp(message.timestamp);
	messageDiv.append(
  	$("<span>").addClass("message-timestamp").text(`[${messageTimestamp}] `),
  	$("<span>")
    .addClass("message-username")
    .text(`${user.name === userName ? 'You' : user.name}: `)
    .css("color", getUserColor(user.id)),
  $("<span>").addClass("message-text").text(message.text)
);
      $("#messageList").append(messageDiv);
    });
  });
}

function getUserColor(userId) {
  const colorMap = {
    "1": "blue",
    "2": "red",
    "3": "green",
    // add more colors as needed
  };
  return colorMap[userId] || "black";
}

function getUsernameFromSessionStorage(userId) {
  const usersJson = sessionStorage.getItem('user');
  if (!usersJson) return null;
  const users = JSON.parse(usersJson);
  const user = users[userId];
  return user ? user.name : null;
}

function getParameterByName(name) {
  const url = window.location.href;
  name = name.replace(/[\[\]]/g, "\\$&");
  const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
    results = regex.exec(url);
  if (!results) return null;
  if (!results[2]) return "";
  return decodeURIComponent(results[2].replace(/\+/g, " "));
}

//function loadUserName(userId) {
//  let userName;
//  try {
//    const storedUser = JSON.parse(sessionStorage.getItem(userId));
//    if (storedUser) {
//      userName = storedUser.name;
//    }
//  } catch (error) {
//    console.error(`Error parsing sessionStorage value for key "${userId}":`, error);
//  }
//  return userName;
//}

function createMessage(channelId, userId, messageText) {

  fetch(`/channel/${channelId}/message?userId=${userId}&text=${encodeURIComponent(messageText)}`, {
    method: 'POST'
  })
    .then(response => {
      console.log('Message created successfully:', response);
      $("#message").val("");
    })
    .catch(error => {
      console.error('Error creating message:', error);
    });
}

function formatTimestamp(timestamp) {
  const date = new Date(timestamp);
  const month = date.toLocaleString("en-US", { month: "short" });
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();
  const ampm = hour >= 12 ? "PM" : "AM";
  const formattedHour = hour % 12 || 12;
  const formattedMinute = minute.toString().padStart(2, "0");

  return `${month} ${day}, ${formattedHour}:${formattedMinute} ${ampm}`;
}