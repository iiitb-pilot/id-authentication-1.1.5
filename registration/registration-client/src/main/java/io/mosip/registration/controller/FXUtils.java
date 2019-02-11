package io.mosip.registration.controller;

import static io.mosip.registration.constants.RegistrationConstants.APPLICATION_NAME;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import io.mosip.kernel.core.logger.spi.Logger;
import io.mosip.registration.config.AppConfig;
import io.mosip.registration.constants.RegistrationConstants;
import io.mosip.registration.context.SessionContext;
import io.mosip.registration.controller.reg.RegistrationController;
import io.mosip.registration.controller.reg.Validations;
import io.mosip.registration.dto.mastersync.GenderDto;
import io.mosip.registration.dto.mastersync.LocationDto;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * Class for JavaFx utilities operation
 * 
 * @author Taleev.Aalam
 * @since 1.0.0
 *
 */
public class FXUtils {

	/**
	 * Instance of {@link Logger}
	 */
	private static final Logger LOGGER = AppConfig.getLogger(RegistrationController.class);

	private static FXUtils fxUtils = null;

	public static FXUtils getInstance() {
		if (fxUtils == null)
			fxUtils = new FXUtils();

		return fxUtils;
	}

	/**
	 * Validator method for field during onType
	 */
	public void validateOnType(TextField field, Validations validation) {
		field.textProperty().addListener((obsValue, oldValue, newValue) -> {
			if (!validation.validateTextField(field, field.getId() + "_ontype",
					(String) SessionContext.map().get(RegistrationConstants.IS_CONSOLIDATED))) {
				field.setText(oldValue);
			}
		});
	}
	
	public void populateLocalComboBox(ComboBox<?> applicationField, ComboBox<String> localField) {
		applicationField.getSelectionModel().selectedItemProperty()
				.addListener((options, oldValue, newValue) -> localField
						.setValue(getSelectedValue(applicationField.getSelectionModel().getSelectedItem())));
	}

	/**
	 * Validator method for field during onType and the local field population
	 */
	public void validateOnType(TextField field, Validations validation, TextField localField) {
		field.textProperty().addListener((obsValue, oldValue, newValue) -> {
			if (!validation.validateTextField(field, field.getId() + "_ontype",
					(String) SessionContext.map().get(RegistrationConstants.IS_CONSOLIDATED))) {
				field.setText(oldValue);
			} else {
				if(localField!=null)
					localField.setText(field.getText());
			}
		});

	}

	public void dobListener(TextField field, TextField fieldToPopulate,String regex) {
		field.textProperty().addListener((obsValue, oldValue, newValue) -> {
			if (field.getText().matches(regex)) {
				int year = Integer.parseInt(field.getText());
				int age = LocalDate.now().getYear() - year;
				if(age>=0&&age<=118)
					fieldToPopulate.setText("" + age);
			}
		});
	}
	
	/**
	 * To display the selected date in the date picker in specific
	 * format("dd-mm-yyyy").
	 */
	public void dateFormatter(DatePicker ageDatePicker) {
		try {
			LOGGER.info(RegistrationConstants.REGISTRATION_CONTROLLER, RegistrationConstants.APPLICATION_NAME,
					RegistrationConstants.APPLICATION_ID, "Validating the date format");

			ageDatePicker.setConverter(new StringConverter<LocalDate>() {
				String pattern = "dd-MM-yyyy";
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

				{
					ageDatePicker.setPromptText(pattern.toLowerCase());
				}

				@Override
				public String toString(LocalDate date) {
					return date != null ? dateFormatter.format(date) : "";
				}

				@Override
				public LocalDate fromString(String string) {
					if (string != null && !string.isEmpty()) {
						return LocalDate.parse(string, dateFormatter);
					} else {
						return null;
					}
				}
			});
		} catch (RuntimeException runtimeException) {
			LOGGER.error("REGISTRATION - DATE FORMAT VALIDATION FAILED ", APPLICATION_NAME,
					RegistrationConstants.APPLICATION_ID, runtimeException.getMessage());
		}
	}

	/**
	 * Disabling the future days in the date picker calendar.
	 */
	public void disableFutureDays(DatePicker ageDatePicker) {
		try {
			LOGGER.info(RegistrationConstants.REGISTRATION_CONTROLLER, RegistrationConstants.APPLICATION_NAME,
					RegistrationConstants.APPLICATION_ID, "Disabling future dates");

			ageDatePicker.setDayCellFactory(picker -> new DateCell() {
				@Override
				public void updateItem(LocalDate date, boolean empty) {
					super.updateItem(date, empty);
					LocalDate today = LocalDate.now();

					setDisable(empty || date.compareTo(today) > 0);
				}
			});

			LOGGER.info(RegistrationConstants.REGISTRATION_CONTROLLER, RegistrationConstants.APPLICATION_NAME,
					RegistrationConstants.APPLICATION_ID, "Future dates disabled");
		} catch (RuntimeException runtimeException) {
			LOGGER.error("REGISTRATION - DISABLE FUTURE DATE FAILED", APPLICATION_NAME,
					RegistrationConstants.APPLICATION_ID, runtimeException.getMessage());
		}
	}

	private String getSelectedValue(Object selectedOption) {
		String selectedValue = RegistrationConstants.EMPTY;
		
		if (selectedOption instanceof LocationDto) {
			selectedValue = ((LocationDto) selectedOption).getName();
		} else if(selectedOption instanceof GenderDto) {
			selectedValue = ((GenderDto) selectedOption).getGenderName();
		} if (selectedOption instanceof String) {
			selectedValue = (String) selectedOption;
		}
		
		return selectedValue;
	}

	public void selectComboBoxValue(ComboBox<?> comboBox, String selectedValue) {
		ObservableList<?> comboBoxValues = comboBox.getItems();

		if (!comboBoxValues.isEmpty()) {
			IntPredicate findIndexOfSelectedItem = null;
			if (comboBoxValues.get(0) instanceof LocationDto) {
				findIndexOfSelectedItem = index -> ((LocationDto) comboBoxValues.get(index)).getName()
						.equals(selectedValue);
			} else if (comboBoxValues.get(0) instanceof GenderDto) {
				findIndexOfSelectedItem = index -> ((GenderDto) comboBoxValues.get(index)).getGenderName()
						.equals(selectedValue);
			}

			OptionalInt indexOfSelectedLocation = getIndexOfSelectedItem(comboBoxValues, findIndexOfSelectedItem);

			if (indexOfSelectedLocation.isPresent()) {
				comboBox.getSelectionModel().select(indexOfSelectedLocation.getAsInt());
			}
		}
	}

	private OptionalInt getIndexOfSelectedItem(ObservableList<?> comboBoxValues, IntPredicate lambdaExpression) {
		return IntStream.range(0, comboBoxValues.size()).filter(lambdaExpression).findFirst();
	}

	public <T> StringConverter<T> getStringConverterForComboBox() {
		return new StringConverter<T>() {
			@Override
			public String toString(T object) {
				String value = null;
				if (object instanceof LocationDto) {
					value = ((LocationDto) object).getName();
				} else if (object instanceof GenderDto) {
					value = ((GenderDto) object).getGenderName();
				}
				return value;
			}

			@Override
			public T fromString(String string) {
				return null;
			}
		};
	}

}
