package main;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Utils {
	
	/**
	* <p>Checks if the specified name is a valid enum for the class.</p>
	*
	* <p>This method differs from {@link Enum#valueOf} in that checks if the name is
	* a valid enum without needing to catch the exception.</p>
	*
	* @param <E> the type of the enumeration
	* @param enumClass  the class of the enum to query, not null
	* @param enumName   the enum name, null returns false
	* @return true if the enum name is valid, otherwise false
	*/	
	public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String enumName){
		if(enumClass == null){
			return false;
		}
		try{
			Enum.valueOf(enumClass, enumName);
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}
	
    /**
     * Monstra uma alert simples
     * @param tipo Tipo de mensagem de alerta
     * @param titulo Titulo da mensagem de alerta
     * @param mensagem Mensagem 
     * @param cabecalhoMensagem Cabeçalho da mensagem. Usar null para esconder o cabeçalho
     * @param showAndWait Se true, o programa espera pela resposta da alert box
     */
    public static void alertBox(Alert.AlertType tipo, String titulo, String mensagem, String cabecalhoMensagem, boolean showAndWait){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.getDialogPane().setContent( new Label(mensagem));
        alerta.setHeaderText(cabecalhoMensagem);
        ((Stage)alerta.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/main/icon_medium.png"));
        if(showAndWait)
            alerta.showAndWait();
        else
            alerta.show();
    }	
    
    public static String toFirstUpper(String text){
    	return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
