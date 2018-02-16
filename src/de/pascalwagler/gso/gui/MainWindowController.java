package de.pascalwagler.gso.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.pascalwagler.gso.GSOAlgorithm;
import de.pascalwagler.gso.GSOParameters;
import de.pascalwagler.gso.Glowworm;
import de.pascalwagler.gso.IterationCallback;
import de.pascalwagler.gso.functions.BealeFunction;
import de.pascalwagler.gso.functions.BoothFunction;
import de.pascalwagler.gso.functions.CirclesFunction;
import de.pascalwagler.gso.functions.DistanceToPoint;
import de.pascalwagler.gso.functions.EasomFunction;
import de.pascalwagler.gso.functions.EggholderFunction;
import de.pascalwagler.gso.functions.EqualPeaksFunction;
import de.pascalwagler.gso.functions.HimmelblauFunction;
import de.pascalwagler.gso.functions.HoelderTableFunction;
import de.pascalwagler.gso.functions.LeviFunctionN13;
import de.pascalwagler.gso.functions.MatyasFunction;
import de.pascalwagler.gso.functions.ObjectiveFunction;
import de.pascalwagler.gso.functions.PeaksFunction;
import de.pascalwagler.gso.functions.PlateausFunction;
import de.pascalwagler.gso.functions.RastriginFunction;
import de.pascalwagler.gso.functions.SchafferN2Function;
import de.pascalwagler.gso.functions.StaircaseFunction;
import de.pascalwagler.gso.vis.ColorMapper;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MainWindowController implements Initializable, IterationCallback {

	@FXML private Canvas canvas;

	@FXML private TextField populationSize;
	@FXML private TextField maxIterations;
	@FXML private TextField initialRange;
	@FXML private CheckBox showTrail;
	@FXML private Slider glowwormSizeSlider;
	@FXML private TextField glowwormSizeTextField;
	@FXML private Slider animationSpeedSlider;
	@FXML private TextField animationSpeedTextField;
	@FXML private ChoiceBox<ObjectiveFunction> functionChoiceBox;
	@FXML private Button resetButton;
	@FXML private Button startStopButton;

	private GraphicsContext gc;
	private double scale;

	private static final int CANVAS_WIDTH = 500;
	private static final Color GLOWWORM_COLOR = Color.BLACK;

	private static final String GITHUB_URL = "https://github.com/Wandmalfarbe/Glowworm-Swarm-Optimization-Java";

	private Image functionImage;
	private ObjectiveFunction function;

	private long lastUpdate = 0;
	private GSOAlgorithm algorithm;
	private AnimationTimer timer;
	private boolean animationIsRunning = false;
	private int animationSpeed = 500;

	public MainWindowController() {

		resetFunction(new PeaksFunction());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		canvas.setWidth( function.getWidth() * scale);
		canvas.setHeight( function.getHeight() * scale);

		List<ObjectiveFunction> functions = new ArrayList<>();
		functions.add(new PeaksFunction());
		functions.add(new RastriginFunction());
		functions.add(new CirclesFunction());
		functions.add(new StaircaseFunction());
		functions.add(new PlateausFunction());
		functions.add(new HimmelblauFunction());
		functions.add(new EqualPeaksFunction());
		functions.add(new BealeFunction());
		functions.add(new BoothFunction());
		functions.add(new MatyasFunction());
		functions.add(new LeviFunctionN13());
		functions.add(new EasomFunction());
		functions.add(new EggholderFunction());
		functions.add(new HoelderTableFunction());
		functions.add(new SchafferN2Function());
		functions.add(new DistanceToPoint());

		functionChoiceBox.getItems().addAll(FXCollections.observableList(functions));
		functionChoiceBox.getSelectionModel().selectFirst();

		functionChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ObjectiveFunction>() {
			@Override
			public void changed(ObservableValue<? extends ObjectiveFunction> observableValue, ObjectiveFunction oldF, ObjectiveFunction newF) {
				resetFunction(newF);
				resetCanvas();
			}
		});

		glowwormSizeSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override public void changed(ObservableValue o,Number oldVal, Number newVal){
				glowwormSizeTextField.setText(Integer.toString(newVal.intValue()));
			}
		});
		glowwormSizeTextField.setText(Double.toString(Math.floor(glowwormSizeSlider.getValue())));

		animationSpeedSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override public void changed(ObservableValue o,Number oldVal, Number newVal){
				animationSpeedTextField.setText(Integer.toString(newVal.intValue()));
				animationSpeed = newVal.intValue();
			}
		});
		animationSpeedTextField.setText(Double.toString(Math.floor(animationSpeedSlider.getValue())));

		this.gc = canvas.getGraphicsContext2D();
		this.resetForm();

		resetCanvas();

		this.lastUpdate = System.nanoTime();
		timer = new AnimationTimer() {

			/*
			 * The AnimationTimer's handle method is invoked once for each frame that is rendered, on the FX Application Thread.
			 */
			public void handle(long now) {

				// 1 sec
				if (now - lastUpdate >= (1_000_000*animationSpeed)) {
					algorithm.step();
					lastUpdate = now ;
				}
			}
		};
	}

	@FXML
	private void startStop() {

		if(animationIsRunning) {

			timer.stop();
			animationIsRunning = false;
			startStopButton.setText("Start");

			enableForm();
		} else {
			resetCanvas();

			GSOParameters parameters = new GSOParameters(
					Integer.parseInt(populationSize.getText()), // population size
					Integer.parseInt(maxIterations.getText()),  // max iterations
					Double.parseDouble(initialRange.getText()), // initial sensor range
					Double.parseDouble(initialRange.getText())  // max sensor range
					);
			algorithm = new GSOAlgorithm(parameters, function, this);
			algorithm.initialize();

			disableForm();

			timer.start();
			animationIsRunning = true;
			startStopButton.setText("Stop");
		}		
	}

	@FXML
	private void openGithubURL() {
		MainWindow.getStaticHostServices().showDocument(GITHUB_URL);
	}

	@FXML
	private void resetForm() {
		populationSize.setText("1000");
		maxIterations.setText("100");
		initialRange.setText("4");
		showTrail.setSelected(true);
		glowwormSizeSlider.setValue(2);
		functionChoiceBox.getSelectionModel().selectFirst();
	}

	private void disableForm() {
		populationSize.setDisable(true);
		maxIterations.setDisable(true);
		initialRange.setDisable(true);
		showTrail.setDisable(true);
		glowwormSizeSlider.setDisable(true);
		functionChoiceBox.setDisable(true);
		resetButton.setDisable(true);
	}

	private void enableForm() {
		populationSize.setDisable(false);
		maxIterations.setDisable(false);
		initialRange.setDisable(false);
		showTrail.setDisable(false);
		glowwormSizeSlider.setDisable(false);
		functionChoiceBox.setDisable(false);
		resetButton.setDisable(false);
	}

	public void resetCanvas(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
		gc.drawImage(functionImage, 0, 0);

		this.gc.setFill(GLOWWORM_COLOR);
	}

	public void resetFunction(ObjectiveFunction f) {

		// Set new function
		this.function = f;

		// Set new scale
		this.scale = CANVAS_WIDTH/function.getWidth();

		// Make a new ColorMapper with the function
		try {
			ColorMapper colorMapper = new ColorMapper(function, (int)(function.getWidth() * scale), (int)(function.getHeight() * scale));
			functionImage = SwingFXUtils.toFXImage(colorMapper.getImage(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Callback method for drawing the glowworms.
	 */
	public void iterationFinished(GSOAlgorithm gso) {

		Glowworm[] glowworms = gso.glowworms;

		Platform.runLater(new Runnable() {
			@Override public void run() {

				if(!showTrail.isSelected()) resetCanvas();
				long size = Math.round(glowwormSizeSlider.getValue());

				for(Glowworm g: glowworms) {
					gc.fillOval(g.posX*scale+(function.getWidth()/2)*scale, g.posY*scale+(function.getHeight()/2)*scale, size, size);
					//gc.strokeOval(g.posX*scale-(g.range*scale), g.posY*scale-(g.range*scale), g.range*scale*2, g.range*scale*2);
				}

				/*WritableImage wim = new WritableImage(CANVAS_WIDTH, CANVAS_WIDTH);
					WritableImage snapshot = canvas.snapshot(null, wim);
					File output = new File("out/gso-" + String.format("%05d", gso.t) + ".png");
	                try {
						ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
					} catch (IOException e) {
						e.printStackTrace();
					}*/
			}
		});
	}
}