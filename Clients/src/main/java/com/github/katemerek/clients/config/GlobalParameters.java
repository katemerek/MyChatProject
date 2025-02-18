//package com.github.katemerek.clients.config;
//
//import com.github.katemerek.clients.clients.Client;
//
//public class GlobalParameters {
//
//    private static volatile GlobalParameters instance;
//    private ThreadLocal<Client> client = new ThreadLocal<>();
//
//    public static GlobalParameters getInstance() {
//        if (instance == null) {
//            synchronized (GlobalParameters.class) {
//                if (instance == null) {
//                    instance = new GlobalParameters();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public ThreadLocal<Client> getClient() {
//        return client;
//    }
//}
