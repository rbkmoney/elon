package com.rbkmoney.logback.mask;

import com.rbkmoney.damsel.domain.TransactionInfo;
import com.rbkmoney.damsel.proxy_provider.PaymentContext;
import com.rbkmoney.damsel.proxy_provider.RecurrentTokenContext;
import org.slf4j.MDC;

import java.util.Map;

public class MdcContext {

    public void mdcPutContext(RecurrentTokenContext context, String[] fieldsToPutInMdc) {
        TransactionInfo transactionInfo = context.getTokenInfo().getTrx();
        String amount = Converter.getFormattedAmount(context.getTokenInfo().getPaymentTool().getMinimalPaymentCost().getAmount()).toString();
        MDC.put("amount", amount);
        mdcPutContextTransactionInfo(transactionInfo, fieldsToPutInMdc);
    }

    public void mdcPutContext(PaymentContext context, String[] fieldsToPutInMdc) {
        TransactionInfo transactionInfo = context.getPaymentInfo().getPayment().getTrx();
        String amount = Converter.getFormattedAmount(context.getPaymentInfo().getPayment().getCost().getAmount()).toString();
        MDC.put("amount", amount);
        mdcPutContextTransactionInfo(transactionInfo, fieldsToPutInMdc);
    }

    public void mdcPutContextTransactionInfo(TransactionInfo transactionInfo, String[] fieldsToPutInMdc) {
        if (transactionInfo != null) {
            Map<String, String> trxextra = transactionInfo.getExtra();
            for (String field : fieldsToPutInMdc) {
                MDC.put(field, trxextra.get(field));
            }
        }
    }

    public void mdcRemoveContext(String[] fieldsToPutInMdc) {
        MDC.remove("amount");
        for (String field : fieldsToPutInMdc) {
            MDC.remove(field);
        }
    }
}