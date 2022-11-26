package com.chatter.view;

import java.util.Objects;

import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Component
class ViewServiceImpl implements ViewService {

	private Stage mainStage;

	@Override
	public void show(Parent parent) {
		if (this.mainStage == null)
			return;

		Objects.requireNonNull(parent, "pane can not be null!");
		Scene scene = this.mainStage.getScene();
		scene.setRoot(parent);
		this.mainStage.sizeToScene();
		this.mainStage.show();
		this.mainStage.centerOnScreen();
		this.mainStage.toFront();

	}

	@Override
	public void setMainStage(Stage stage) {
		this.mainStage = Objects.requireNonNull(stage, "stage must not be null!");
		StackPane dummy = new StackPane();
		Scene scene = new Scene(dummy);
		this.mainStage.setScene(scene);
	}

}
