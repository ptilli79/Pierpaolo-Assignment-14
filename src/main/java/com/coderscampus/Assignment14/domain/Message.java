package com.coderscampus.Assignment14.domain;

import java.time.LocalDateTime;


//Message.java
public class Message {
 private String id;
 private User user;
 private Channel channel;
 private String text;
 private LocalDateTime timestamp;

 public Message(String id, User user, Channel channel, String text, LocalDateTime timestamp) {
     this.id = id;
     this.user = user;
     this.channel = channel;
     this.text = text;
     this.timestamp = timestamp;
 }

 public String getId() {
     return id;
 }

 public void setId(String id) {
     this.id = id;
 }

 public User getUser() {
     return user;
 }

 public void setUser(User user) {
     this.user = user;
 }

 public Channel getChannel() {
     return channel;
 }

 public void setChannel(Channel channel) {
     this.channel = channel;
 }

 public String getText() {
     return text;
 }

 public void setText(String text) {
     this.text = text;
 }

 public LocalDateTime getTimestamp() {
     return timestamp;
 }

 public void setTimestamp(LocalDateTime timestamp) {
     this.timestamp = timestamp;
 }
}