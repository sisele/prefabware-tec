package org.mockito.configuration;

import org.mockito.stubbing.Answer;

/**
 * the only way to globaly configure Mockito is to provide a class
 * with exactly the name org.mockito.configuration.MockitoConfiguration
 * So there can only be one in the classpath at any time !
 *
 * Created by stefan on 01.02.17.
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration{
	/**
	 * to enable this configuration
	 *
	 */
	public static boolean stricktMocking =false;
	Answer<Object> answer=new UnexpectedInvocationAnswer<>();

	@Override
	public Answer<Object> getDefaultAnswer() {
		if(stricktMocking){
			return answer;
		}else{
			return super.getDefaultAnswer();
		}
	}
}
