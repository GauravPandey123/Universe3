package in.editsoft.api.util;

import in.editsoft.api.exception.APIException;

/**
 * Validation
 */
public class Validation {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() <= 0;
    }

    public static boolean isValidMobileNumber(String number) throws APIException {
        if (isNullOrEmpty(number)) {
            throw new APIException(APIException.Kind.NULL, APIMessage.NUMBER_CAN_NOT_BE_EMPTY);
        }
        return number.matches("^[789][0-9]{9}$");
    }

    public static boolean isValidEmail(String email) throws APIException {
        if (isNullOrEmpty(email)) {
            throw new APIException(APIException.Kind.NULL, APIMessage.EMAIL_CAN_NOT_BE_EMPTY);
        }
        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
                "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public static boolean isValidPinCode(String pinCode) throws APIException {
        if (isNullOrEmpty(pinCode)) {
            throw new APIException(APIException.Kind.NULL, APIMessage.PIN_CODE_CAN_NOT_BE_EMPTY);
        }
        return pinCode.matches("^[0-9]{6}$");
    }
}
