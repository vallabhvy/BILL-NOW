package in.vallabh.billingsoftware.service;

import com.razorpay.RazorpayException;
import in.vallabh.billingsoftware.io.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
