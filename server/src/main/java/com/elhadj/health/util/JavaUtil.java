package com.elhadj.health.util;

import org.modelmapper.ModelMapper;

public class JavaUtil {
	public static boolean notNullAndEmpty(String s) {
		return (s != null && !s.isEmpty());
	}
	
	public static <T,U> U convertTo(T post, Class<?> UClass) {
		ModelMapper mp = new ModelMapper();
	    U postDto = (U) mp.map(post, UClass);
	    return postDto;
	}
}
