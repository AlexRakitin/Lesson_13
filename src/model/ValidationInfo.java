package model;

public class ValidationInfo {
    public static final String EMPTY_DESCRIPTION = "";
    private boolean isValid;

    private String invalidationExplanation;

    public ValidationInfo(boolean isValid, String invalidationExplanation) {
        this.isValid = isValid;
        this.invalidationExplanation = invalidationExplanation;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getInvalidationExplanation() {
        return invalidationExplanation;
    }

    public void setInvalidationExplanation(String invalidationExplanation) {
        this.invalidationExplanation = invalidationExplanation;
    }

    @Override
    public String toString() {
        if (isValid) {
            return "Valid";
        } else {
            return "Not Valid. Reason: " + invalidationExplanation;
        }
    }
}
