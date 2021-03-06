import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JSlider;

import com.fazecast.jSerialComm.SerialPort;

public class AnalogReadSerial extends JFrame {

	public AnalogReadSerial() {
		JFrame window = new JFrame();
		JSlider slider = new JSlider();
		slider.setMaximum(1023);
		window.add(slider);
		window.pack();
		window.setVisible(true);

		SerialPort[] ports = SerialPort.getCommPorts();
		System.out.println("Select a port:");
		int i = 1;
		for (SerialPort port : ports)
			System.out.println(i++ + ": " + port.getSystemPortName());
		Scanner s = new Scanner(System.in);
		int chosenPort = s.nextInt();

		SerialPort serialPort = ports[chosenPort - 1];
		if (serialPort.openPort())
			System.out.println("Port opened successfully.");
		else {
			System.out.println("Unable to open the port.");
			return;
		}
		serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);

		Scanner data = new Scanner(serialPort.getInputStream());
		int value = 0;
		while (data.hasNextLine()) {
			try {
				value = Integer.parseInt(data.nextLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(value);
			slider.setValue(value);
		}
		System.out.println("Done.");
	}

	public static void main(String[] args) {
		try {
			new AnalogReadSerial();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}