package com.bridgelabz.fundoo.note.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.elasticserach.ElasticSearch;
import com.bridgelabz.fundoo.elasticserach.ElasticsearchImlementation;
import com.bridgelabz.fundoo.note.dto.CollaboratorDTO;
import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.services.NoteService;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.utility.ResponseInfo;
import com.bridgelabz.fundoo.utility.UserToken;

@RestController
@CrossOrigin(origins="http://localhost:4200")

public class NoteController {
	static final Logger logger = LoggerFactory.getLogger(NoteController.class);
	@Autowired
	private NoteService noteServices;
	@Autowired
	Environment environment;
//	@Autowired
//	ElasticsearchImlementation elasticSearch;
//
//	@Autowired
//	private UserToken userToken;

	/*
	 * @Autowired Response response;
	 */

	@PostMapping("/createNote")
	public ResponseEntity<Response> Create(@RequestBody NoteDto noteDto, @RequestHeader("jwt_Token") String token) {
		System.out.println("ashjdghasgdhjasdgfhjasgf");
		logger.info("Note DTO:" + noteDto);
		logger.trace("Create note:");
		Response response = noteServices.create(noteDto, token);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping("/updateNote/{noteId}")
	public ResponseEntity<Response> UpdateNote(@RequestBody NoteDto noteDto,@RequestHeader("jwt_Token") String token,
			@PathVariable long noteId) {
		logger.info("Note DTO:" + noteDto);
		logger.trace("Update note:");
		Response response = noteServices.updateNote(noteDto, token, noteId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/deleteNote/{noteId}")
	public ResponseEntity<Response> deleteNotePermanently(@PathVariable Long noteId, @RequestHeader("jwt_Token") String token)
	{
		logger.info("Note Id:" + noteId);
		logger.trace("Delete note:");
		Response response = noteServices.deleteNotePermanently(noteId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/allNotes")
	public ResponseEntity<List<Note>> getAllNotes(@RequestHeader("jwt_Token") String token,@RequestParam boolean archived,@RequestParam boolean trashed)  {
		logger.info("Token:" + token);
		logger.info("Get all notes:");
		List<Note> allNotes = noteServices.getAllNote(token,archived,trashed);
		return new ResponseEntity<>(allNotes, HttpStatus.OK);

	}

	@GetMapping("/note/{noteId}")
	public Note getNote(@RequestHeader String token, @PathVariable Long noteId) {
		logger.info("Token:" + token);
		logger.info("Note Id:" + noteId);
		logger.trace("Get note by id:");
		Note note = noteServices.getNote(token, noteId);
		return note;
	}

	@PutMapping("/pinNote/{noteId}")
	public ResponseEntity<Response> pinnedUnpinned(@RequestHeader("jwt_Token") String token, @PathVariable Long noteId)
	{
		logger.info("Token:" + token);
		logger.info("Note Id:" + noteId);
		logger.trace("Pinned/Unpinned by id:");
		System.out.println("hello");
		Response response = noteServices.pinnedUnpinned(token, noteId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping("/trashNote/{noteId}")
	public ResponseEntity<Response> trashedUntrashed(@RequestHeader("jwt_Token") String token, @PathVariable Long noteId)
	{
		logger.info("Token:" + token);
		logger.info("Note Id:" + noteId);
		logger.trace("Trashed/Untrashed by id:");
		Response response = noteServices.trashedUntrashed(token, noteId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping("/archiveNote/{noteId}")
	public ResponseEntity<Response> archiveUnarchive(@RequestHeader("jwt_Token") String token, @PathVariable Long noteId)
	{
		logger.info("Token:" + token);
		logger.info("Note Id:" + noteId);
		logger.trace("Trashed/Untrashed by id:");
		Response response = noteServices.archiveUnarchive(token, noteId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// add label to the note
	// remove label from the note
	@PostMapping("/{noteId}/{labelId}")
	public ResponseEntity<Response> addLabel(@RequestHeader String token, @PathVariable long noteId,
			@PathVariable long labelId) {
		logger.info("token: " + token);
		logger.trace("add label to note");
		Response response;
		//		if (labelDTO.getName().equals("") || labelDTO.getName() == null)
		//			response = ResponseInfo.getResponse(-800, "label can't not be empty");
		//		else
		response = noteServices.addLabelToNote(token, noteId, labelId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/notes/{noteId}/{labelId}")
	public ResponseEntity<Response> removeLabel(@RequestHeader String token, @PathVariable long noteId,
			@PathVariable long labelId) {
		logger.info("token: " + token);
		logger.trace("remove label to note");
		Response response;
		//		if (labelDTO.getName().equals("") || labelDTO.getName() == null)
		//			response = ResponseInfo.getResponse(-800, "label can't not be empty");
		//		else
		response = noteServices.removeLabelFromNote(token, noteId, labelId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PostMapping("/notes/{id}")
	public ResponseEntity<Response> addReminder(@PathVariable(value="id") long noteId,@RequestHeader("jwt_Token") String token,@RequestParam String time)throws ParseException  

	{

		System.out.println("Note Created");
		Response response= noteServices.ReminderSet(noteId,time,token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);

	}
	@PostMapping("/notes/remove/{id}")
	public ResponseEntity<Response> removeReminder(@PathVariable(value="id") long noteId,@RequestHeader("jwt_Token") String token) throws ParseException 

	{

		System.out.println("Note Created");
		Response response= noteServices.ReminderRemove(noteId,token);

		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}
	@PostMapping("/notes/color/{id}")
	public ResponseEntity<Response> noteColor(@PathVariable(value="id") long noteId,@RequestParam String color,@RequestHeader("jwt_Token") String token)
	{
		Response response= noteServices.colorSet(noteId,color,token);

		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}

	@PutMapping("/notes/addcollaborator/{id}")
	public ResponseEntity<Response> addCollaboratedUser(@PathVariable(value="id") long noteId,@RequestParam String email,@RequestHeader("jwt_Token") String token)
	{
		Response response= noteServices.addCollaboratedUser(noteId, email, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}

	@GetMapping("/notes/removecollaborator/{id}")
	public ResponseEntity<Response> removeCollaboratedUser(@RequestParam(value="id") long noteId,@RequestHeader("jwt_Token") String token, @RequestParam String email)
	{
		Response response= noteServices.removeCollaboratedUser(noteId, email, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}
	@GetMapping("/note/list/collab")
	public List<User> getNote(@RequestHeader(value="jwt_token") String token,@RequestParam long noteId)
	{
		
		List<User> response=noteServices.getCollabNote(token,noteId);
		 System.out.println(response);
		
	     return response;
		 
	}
	
	@GetMapping("/note/search")
	public List<Note> getNote(@RequestParam String query,@RequestHeader(value="jwt_token") String token)
	{
		  System.out.println("hiii");
		List<Note> response=noteServices.searchNote(query,token);
		
	  System.out.println(response);

	  System.out.println("hiii");
			return response;

		 
	}


}
