package com.onrender.todolistjeed.todolistapi.Models;

import lombok.Data;

@Data
public class StatusTaskModel {
    private Long taskID;
    private boolean status;

    public boolean getStatus () {
        return status;
    }
}
