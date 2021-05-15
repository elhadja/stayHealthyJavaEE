package com.elhadj.health;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Util {
	List<Long> ids = new ArrayList<>();
	
	public void add(long id) {
		ids.add(id);
	}
	
	public <T> void deleteAll(BiConsumer<T, Long> f, T arg) {
		 for (long id: ids) {
			 f.accept(arg, id);
		 }
		 ids.clear();
	}

	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
	    try {
			String contentAsString = result.getResponse().getContentAsString();
	        //return MAPPER.readValue(contentAsString, responseClass);
		    //new ObjectMapper().
			Gson gson = new Gson();
			return gson.fromJson(contentAsString, responseClass);
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }
	}
}