package com.straw.friend.pojo;

import java.util.List;

import org.androidannotations.annotations.EBean;

import com.straw.friend.bean.Joke;
@EBean
public class JokeReturn {
    List<Joke> content;

	public List<Joke> getContent() {
		return content;
	}

	public void setContent(List<Joke> content) {
		this.content = content;
	}
    
}
