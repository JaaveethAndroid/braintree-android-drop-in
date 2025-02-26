package com.braintreepayments.api;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MockPayPalClientBuilder {

    private Exception browserSwitchError;
    private PayPalAccountNonce browserSwitchResult;

    public MockPayPalClientBuilder browserSwitchError(Exception browserSwitchError) {
        this.browserSwitchError = browserSwitchError;
        return this;
    }

    public MockPayPalClientBuilder browserSwitchResult(PayPalAccountNonce browserSwitchResult) {
        this.browserSwitchResult = browserSwitchResult;
        return this;
    }

    PayPalClient build() {
        PayPalClient payPalClient = mock(PayPalClient.class);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                PayPalBrowserSwitchResultCallback callback = (PayPalBrowserSwitchResultCallback) invocation.getArguments()[1];
                if (browserSwitchResult != null) {
                    callback.onResult(browserSwitchResult, null);
                } else if (browserSwitchError != null) {
                    callback.onResult(null, browserSwitchError);
                }
                return null;
            }
        }).when(payPalClient).onBrowserSwitchResult(any(BrowserSwitchResult.class), any(PayPalBrowserSwitchResultCallback.class));

        return payPalClient;
    }
}
