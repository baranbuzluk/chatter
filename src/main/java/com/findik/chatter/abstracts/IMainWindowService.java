package com.findik.chatter.abstracts;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface IMainWindowService {

	void setMainStage(Stage mainStage);

	void show(Pane pane);

}
