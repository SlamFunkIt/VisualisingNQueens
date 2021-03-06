package GeneticAlgorithm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Backtracking.Backtracking;
import Backtracking.SolverUtils;
import Backtracking.HillClimbing.acn;
import Backtracking.HillClimbing.ntio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Main {
	public static JFrame jFrame;
	public static int PoP = 20000;
	public static int SP = 200;
	public static int M = 20;
	static Thread m;
	private static JLabel[][] jLabel;
	static JPanel Q, P;
	public static ImageIcon img = SolverUtils.imgo;

	public static void reset() {
		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < M; ++j) {

				if ((i % 2) == (j % 2)) {
					jLabel[i][j].setBackground(Color.WHITE);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				} else {
					jLabel[i][j].setBackground(Color.BLACK);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				}
			}
		}
	}

	public Main() {
		jFrame = new JFrame("GeneticAlg Solver");
		jFrame.setResizable(true);
		jFrame.setLayout(new BorderLayout());
		jFrame.setLocation(550, 0);
		jFrame.setIconImage(img.getImage());
		jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		jLabel = new JLabel[M][M];
		P = new JPanel(new GridBagLayout());
		Q = new JPanel(new GridLayout(M, M));
		acn ao = new acn();
		ntio re = new ntio();

		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < M; ++j) {
				jLabel[i][j] = new JLabel("");
				jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				jLabel[i][j].setSize(50, 50);
				jLabel[i][j].setOpaque(true);
				if ((i % 2) == (j % 2)) {
					jLabel[i][j].setBackground(Color.WHITE);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				} else {
					jLabel[i][j].setBackground(Color.BLACK);
					jLabel[i][j].setIcon(null);
					jLabel[i][j].setText("");
				}
				Q.add(jLabel[i][j]);
			}
		}

		jFrame.setSize(800, 770);
		jFrame.setDefaultCloseOperation(jFrame.DISPOSE_ON_CLOSE);
		Q.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		jFrame.add(Q, BorderLayout.CENTER);
		jFrame.add(P, BorderLayout.SOUTH);
		jFrame.setVisible(true);

	}

	public static void solveG() {

		GeneticAlgo geneticAlgo = new GeneticAlgo(PoP);
		int generation = 1;
		int y = 0;
		for (int j = 0; j < M; j++) {
			for (int i = 0; i < M; i++) {
				if (geneticAlgo.getFittestChromosome().genes[y] == i) {
					jLabel[i][j].setBackground(Color.ORANGE);
					jLabel[i][j].setIcon(img);
					y++;
					break;
				}
			}

		}

		double startTime = System.nanoTime();
		while (geneticAlgo.getFittestChromosome().getFitness() != 0) {
			jFrame.setTitle("GeneticAlg Solver, generation: " + generation + " Fitness:"
					+ geneticAlgo.getFittestChromosome().getFitness());

			geneticAlgo.naturalSelection();

			reset();
			y = 0;
			for (int j = 0; j < M; j++) {
				for (int i = 0; i < M; i++) {
					if (geneticAlgo.getFittestChromosome().genes[y] == i) {
						jLabel[i][j].setBackground(Color.ORANGE);
						jLabel[i][j].setIcon(img);
						y++;
						break;
					}
				}

			}

			try {
				Thread.sleep(SP);
			} catch (Exception e) {
				// Do nothing
			}
			generation++;

		}
		double endTime = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000;
		if (geneticAlgo.getFittestChromosome().getFitness() == 0) {
			String ss = String.format("Time elapsed: %.3f s", totalTime);
			JOptionPane.showMessageDialog(null, "Done!" + "\n" + ss);
			jFrame.setTitle("GeneticAlg Solver, generation: " + generation + " Fitness: "
					+ geneticAlgo.getFittestChromosome().getFitness());
		}
		GUI.QN.setEnabled(true);
		GUI.QZ.setEnabled(true);
		GUI.QX.setEnabled(true);
		GUI.QC.setEnabled(true);
	}

	public static void main(String[] args) {
		new Main();
		solveG();
	}

	public static class aktion implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			m = new Thread(new Runnable() {
				@Override
				public void run() {

					new Main();
					solveG();

				}
			});

			m.start();

		}
	}
}
