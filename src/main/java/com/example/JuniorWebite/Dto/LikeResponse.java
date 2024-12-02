package com.example.JuniorWebite.Dto;

import com.example.JuniorWebite.Entity.Like;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class LikeResponse {


    private List<Like> data;
        private String state ;
        private String message ;

    public LikeResponse(String failed, String message) {
    }

    public List<Like> getData() {
            return data;
        }

        public void setData(List<Like> data) {
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

