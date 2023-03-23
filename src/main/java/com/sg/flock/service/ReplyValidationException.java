/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flock.service;

/**
 *
 * @author nicho
 */
public class ReplyValidationException extends Exception{
    
    public ReplyValidationException(String message) {
        super(message);
    }
    
    public ReplyValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
