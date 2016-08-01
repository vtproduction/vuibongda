/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author son
 */
public class IWTrafficCalculation {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int freqNo = 0;
		System.out.print("So cap tan so = ");
		freqNo = sc.nextInt();
		System.out.print("GoS = ");
		double gos = sc.nextDouble();
		System.out.print("Calls/Subcriber trong gio ban = ");
		int call = sc.nextInt();
		System.out.print("Mean calling time = ");
		int callTime = sc.nextInt();
		System.out.print("So MS tich cuc trong gio ban = ");
		int subscriber = sc.nextInt();
		System.out.println("Solution:");
		System.out.println("As we can see on the image, the number of active MS in busy hour is " + subscriber);
		System.out.println("The traffic of 1 MS is:");
		double trafficPerMS = ((double) call * callTime) / 3600;
		System.out.println("A = C*t/T = " + call + "*" + callTime + "/3600 = " + trafficPerMS + " Erl");
		System.out.println("Traffic on 1 km2 in busy hour is:");
		double trafficPerKm2 = trafficPerMS * subscriber;
		System.out.println("A = " + subscriber + "MS/km2 * " + trafficPerMS + "Erl/MS = " + trafficPerKm2 + " Erl/km2");
		System.out.println("Each cell is assigned "+freqNo+" pairs of frequency then the number of channel in each cell is:");
		int channel = freqNo * 8 - 2;
		System.out.println(freqNo + "* 8 - 2 = " + channel + "channels");
		System.out.print("GoS = " + gos + "% so offered traffic in 1 cell is:");
		double Ao = sc.nextDouble();
		System.out.println("The area of 1 cell is:");
		double area = Ao / trafficPerKm2;
		System.out.println("S = " + Ao + "(Erl)/" + trafficPerKm2 + "(Erl/km2) = " + area + " km2");
		System.out.println("Since the area of cell is calculated as S = 2.6*R^2 then the maximum radius of cell is:");
		double r = Math.sqrt(area / 2.6);
		System.out.println("R = " + r + " km");
		System.out.print("C = ");
		double C = sc.nextDouble();
		System.out.println("The number of cell to cover town C is:");
		double cell = C / area; 
		System.out.println("Number of cell = " + C + "/" + area + "= " + cell + " = " + Math.ceil(cell));
	}

	/**
	 * 
	 * @param freqNo
	 * @param gos
	 * @param callPerSub
	 * @param callTime
	 * @param subscriber
	 * @param offerTraffic
	 * @param C
	 * @return [trafficPerMS, trafficPerKm2, channel, area, radius, numberOfCell]
	 */
	private List  trafficCalculate(int freqNo, double gos, int callPerSub, int callTime, int subscriber, double offerTraffic, double C){
		double trafficPerMS = ((double) callPerSub * callTime) / 3600;
		double trafficPerKm2 = trafficPerMS * subscriber;
		int channel = freqNo * 8 - 2;
		double area = offerTraffic / trafficPerKm2;
		double r = Math.sqrt(area / 2.6);
		double cell = C / area; 
		ArrayList list = new ArrayList();
		list.add(trafficPerMS);
		list.add(trafficPerKm2);
		list.add(channel);
		list.add(area);
		list.add(r);
		list.add(cell);
		return list;
	}
}
