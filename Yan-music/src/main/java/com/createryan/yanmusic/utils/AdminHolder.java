package com.createryan.yanmusic.utils;

import com.createryan.yanmusic.dto.AdminDTO;

public class AdminHolder {
    private static final ThreadLocal<AdminDTO> tl = new ThreadLocal<>();

    public static void saveAdmin(AdminDTO user){
        tl.set(user);
    }

    public static AdminDTO getAdmin(){
        return tl.get();
    }

    public static void removeAdmin(){
        tl.remove();
    }
}