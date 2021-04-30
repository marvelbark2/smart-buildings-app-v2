package edu.episen.si.ing1.pds.client.swing.location;


import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MapCity implements Way {

	public void begin(LocationMenu men) {
		JPanel panel = men.getApp().getContext();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		Request request = new Request();
		request.setEvent("location_building_byid");


		Map<String, Integer> hm = new HashMap<>();
		hm.put("building_id", 2);
		request.setData(hm);

		Response response = Utils.sendRequest(request);

		Map<String, Object>  data = (Map<String, Object>) response.getMessage();

		JLabel name = new JLabel(data.get("name").toString());
		JButton id = new JButton(data.get("id").toString());

		panel.add(name);
		panel.add(id);
	}

}
