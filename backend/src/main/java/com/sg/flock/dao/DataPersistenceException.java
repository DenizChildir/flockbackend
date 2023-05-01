/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flock.dao;

/**
 *
 * @author nicho
 */
public class DataPersistenceException extends Exception{
    
    public DataPersistenceException(String message) {
        super(message);
    }
    
    public DataPersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
