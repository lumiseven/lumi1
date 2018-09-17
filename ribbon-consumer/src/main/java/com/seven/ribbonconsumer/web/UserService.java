package com.seven.ribbonconsumer.web;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

import rx.Observable;

/**
 * 
 * @author seven
 *
 * @description only for test
 */
@Service
public class UserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 
	 * @param id
	 * @return
	 * 同步
	 */
	@HystrixCommand
	public User getUserById(Long id) {
		return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * 异步实现
	 */
	@HystrixCommand
	public Future<User> getUserByIdAsync(final String id) {
		return new AsyncResult<User>() {
			@Override
			public User invoke() {
				return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
			}
			
		};
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * observable 实现(响应式命令)
	 * 
	 * observableExecutionMode 默认Eager表示使用observe()执行方式
	 * 							  Lazy表示使用toObservable()执行方式
	 */
	@HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
	public Observable<User> getUserById(final String id) {
		return Observable.create((t) -> {
			try {
				if (!t.isUnsubscribed()) {
					User user = restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
					t.onNext(user);
					t.onCompleted();
				}
			} catch (RestClientException e) {
				t.onError(e);
			}
		});
	}

}
