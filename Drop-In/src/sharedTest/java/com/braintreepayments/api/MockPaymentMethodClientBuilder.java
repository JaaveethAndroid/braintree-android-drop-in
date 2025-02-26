package com.braintreepayments.api;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MockPaymentMethodClientBuilder {

    private List<PaymentMethodNonce> paymentMethodNonceList;
    private Exception getPaymentMethodNoncesError;

    public MockPaymentMethodClientBuilder getPaymentMethodNoncesSuccess(List<PaymentMethodNonce> paymentMethodNonceList) {
        this.paymentMethodNonceList = paymentMethodNonceList;
        return this;
    }

    public MockPaymentMethodClientBuilder getPaymentMethodNoncesError(Exception error) {
        getPaymentMethodNoncesError = error;
        return this;
    }

    public PaymentMethodClient build() {
        PaymentMethodClient paymentMethodClient = mock(PaymentMethodClient.class);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                GetPaymentMethodNoncesCallback callback = (GetPaymentMethodNoncesCallback) invocation.getArguments()[0];
                if (paymentMethodNonceList != null) {
                    callback.onResult(paymentMethodNonceList, null);
                } else if (getPaymentMethodNoncesError != null) {
                    callback.onResult(null, getPaymentMethodNoncesError);
                }
                return null;
            }
        }).when(paymentMethodClient).getPaymentMethodNonces(any(GetPaymentMethodNoncesCallback.class));

        return paymentMethodClient;
    }
}
