package com.example.JuniorWebite.Dto;

import com.example.JuniorWebite.Entity.Story;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponse {
        private List<Story> data;
        private String state ;
        private String message ;

        public List<Story> getData() {
            return data;
        }

        public void setData(List<Story> data) {
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


