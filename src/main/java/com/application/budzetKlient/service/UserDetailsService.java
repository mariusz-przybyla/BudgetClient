//package com.application.budzetKlient.service;
//
//import com.application.budzetKlient.model.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.io.Serializable;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class UserDetailsService implements Serializable {
//
////    private String previousHandle;
//
////    public void store(UserDetails userDetails) throws ServiceException {
////
////        if (previousHandle == null || !previousHandle.equals(userDetails.getFirstname())) {
////            previousHandle = userDetails.getFirstname();
////            throw new ServiceException("Wyjątek symuluje błąd w backendzie i jest zamierzony. Spróbuj ponownie przesłać formularz.");
////        }
////    }
//
//    public String validateLogin(String login) {
//
//        if (login == null) {
//            return "Pole użytnika nie może być puste";
//        }
//        if (login.length() < 4) {
//            return "Użytkownik nie może być krótszy niż 4 znaki";
//        }
//        List<String> reservedHandles = Arrays.asList("admin", "test", "null", "void");
//        if (reservedHandles.contains(login)) {
//            return String.format("'%s' is not available as a handle", login);
//        }
//
//        return null;
//    }
//
//    public static class ServiceException extends Exception {
//        public ServiceException(String msg) {
//            super(msg);
//        }
//    }
//}
