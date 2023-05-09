// Runs when the DOM is fully loaded
$(document).ready(() => {
	window.sessionStorage.clear();
    loadChannels();
    loadUsers();
    addEventHandlers();
    //setInterval(loadUsers, 500); // Periodically update the user list every 0.5 seconds
});

function addEventHandlers() {
	
	loadUsers();
	
    // Event handler for joining a chat room
    $("#join").click(handleJoinClick);


    // Event handler for creating a new chat room
    $("#createChannel").click(handleCreateChannelClick);
    
// Event handler for click on user radio button
$("#userList").on("click", "input[type='radio']", handleUserRadioClick);

    // Event handler for channel click
    $("#channelList").on("click", "a", handleChannelClick);
}

function loadUsers() {
    $.get("/users", (users) => {
        const userList = $("#userList");
        userList.empty();
        const ul = $("<ul>").addClass("user-list"); // Create a new <ul> element and add a class
        users.forEach((user) => {
            //sessionStorage.setItem(user.id, JSON.stringify(user));
            const userItem = $("<li>").addClass("user-item"); // Add a class to the <li> element
            const userRadio = $("<input>").attr("type", "radio").attr("name", "user").attr("value", user.id);
            const userName = $("<span>").text(user.name);
            userItem.append(userRadio).append(userName);
            ul.append(userItem); // Append the <li> to the <ul>
        });
        userList.append(ul); // Append the <ul> to the #userList
    }).done(() => {
        $(".user-radio").on("click", handleUserRadioClick);
    });
}

function handleUserRadioClick(event) {
    const userId = $(event.target).val();
    getUserById(userId);
}

function addUserToList(user) {
    const userCheckbox = $("<input>")
        .attr("type", "checkbox")
        .attr("id", `user-${user.id}`)
        .attr("class", "user-checkbox")
        .val(user.name); // Set the value to the user name
    const userLabel = $("<label>")
        .attr("for", `user-${user.id}`)
        .text(user.name);
    $("#userList").append(userCheckbox).append(userLabel).append("<br>");
}

function handleChannelClick(event) {
    event.preventDefault();
    const channelId = $(event.target).data("channelId");
    const userId = $("input[type='radio'][name='user']:checked").val(); // Get the value of the checked radio button
    console.log("Selected user ID:", userId); // Add this line for debugging

    if (!userId) {
        alert("Please select a user from the list before joining a channel.");
    } else {
        window.open(`/channels/${channelId}?userId=${userId}`, '_blank');
    }
}

function handleJoinClick() {
    const userName = $("#name").val();
    if (userName) {
        //sessionStorage.setItem("userName", userName);
        createUser(userName);
    }
}

function handleCreateChannelClick() {
    const channelName = $("#newChannel").val();
    if (channelName) {
        createChannel(channelName);
    }
}

// Function to load chat channels
function loadChannels() {
    $.get("/channels", (channels) => {
        $("#channelList").empty();
        channels.forEach((channel) => {
            const channelLink = $("<a>")
                .attr("href", `/channels/${channel.id}`)
                .attr("data-channel-id", channel.id)
                .text(channel.name);
            $("#channelList").append(channelLink).append("<br>");
        });
    });
}

function createUser(name) {
  fetch('/user', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams({ name: name })
  })
    .then(async response => {
      if (response.status === 409) {
        const errorMessage = await response.text();
          throw new Error(errorMessage);
      } else if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json();
    })
    .then(user => {
      console.log('User created:', user);
      //sessionStorage.setItem('user', JSON.stringify(user)); // Store the user in sessionStorage
      storeUser(user);
      loadUsers();
    })
    .catch(error => {
      console.error('Error:', error.message);
      // Display a human-readable error message using an alert
      alert(`Error: ${error.message}`);
    });
}

function getUserById(userId) {
  const usersJson = sessionStorage.getItem('user');
  
  if (usersJson) {
    const users = JSON.parse(usersJson);

    if (users.hasOwnProperty(userId)) {
      const user = users[userId];
      console.log(`User information for ID ${userId}:`, user);
      return user;
    }
  }

  console.log(`User with ID ${userId} not found in session storage.`);
  return null;
}

function storeUser(user) {
  const usersJson = sessionStorage.getItem('user');
  let users = {};

  if (usersJson) {
    users = JSON.parse(usersJson);
  }

  users[user.id] = user;
  sessionStorage.setItem('user', JSON.stringify(users));
}



// Function to create a new chat channel
function createChannel(name) {
    $.post("/channel", { name: name }, () => {
        loadChannels();
        $("#newChannel").val("");
    });
}