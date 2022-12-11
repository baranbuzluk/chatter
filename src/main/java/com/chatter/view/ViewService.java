package com.chatter.view;

import javafx.scene.Parent;
import javafx.stage.Stage;

interface ViewService {

	void setMainStage(Stage mainStage);

	void show(Parent parent);

}
