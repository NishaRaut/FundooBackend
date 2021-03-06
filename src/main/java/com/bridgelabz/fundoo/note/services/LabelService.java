package com.bridgelabz.fundoo.note.services;

import java.util.List;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.response.Response;

public interface LabelService {
	public Response createLabel(LabelDto labelDto, String token);
	public Response updateLabel(LabelDto labelDto,String token,long labelId);
	public Response deleteLable( Long labelId, String token);
	List<Label> getAllLabels(String token);
}
