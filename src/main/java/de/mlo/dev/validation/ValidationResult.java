package de.mlo.dev.validation;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mlo
 */
public class ValidationResult {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final List<ValidationInfo> infos = new ArrayList<>();
    private boolean valid = true;

    @NotNull
    public ValidationResult add(@NotNull ValidationResult validationResult) {
        return add(validationResult.infos);
    }

    @NotNull
    public ValidationResult add(@NotNull ValidationInfo first, ValidationInfo... more) {
        add(first);
        for (ValidationInfo info : more) {
            this.add(info);
        }
        return this;
    }

    public ValidationResult add(Collection<ValidationInfo> validationInfos) {
        for (ValidationInfo info : validationInfos) {
            add(info);
        }
        return this;
    }

    @NotNull
    public ValidationResult add(ValidationInfo validationInfo) {
        if (validationInfo != null) {
            this.infos.add(validationInfo);
            this.valid = valid && validationInfo.isValid();
        }
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isInvalid() {
        return !valid;
    }

    public List<ValidationInfo> getInfos() {
        return new ArrayList<>(infos);
    }

    @NotNull
    public String getMessage() {
        return getMessage(LINE_SEPARATOR);
    }

    @NotNull
    public String getMessage(String delimiter) {
        return infos.stream()
                .map(ValidationInfo::getMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(delimiter));
    }

    public List<String> getMessages(){
        return infos.stream()
                .map(ValidationInfo::getMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
