package com.example.JuniorWebite.Dto;

import com.example.JuniorWebite.Entity.Comment;
import com.example.JuniorWebite.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

        private List<Comment> data;
        private String state ;
        private String message ;

    public CommentResponse(String failed, String message) {
    }

    public List<Comment> getData() {
            return data;
        }

        public void setData(List<Comment> data) {
            this.data = data;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


