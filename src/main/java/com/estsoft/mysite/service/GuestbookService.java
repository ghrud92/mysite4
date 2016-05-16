package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estsoft.mysite.domain.Guestbook;
import com.estsoft.mysite.repository.GuestbookRepository;

@Service
@Transactional
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<Guestbook> getList(){
		List<Guestbook> list = guestbookRepository.getList();
		return list;
	}
	
	public List<Guestbook> getList(int page){
		List<Guestbook> list = guestbookRepository.getList(page);
		return list;
	}
	
	public void insert(Guestbook guestbook){
		guestbookRepository.insert(guestbook);
	}
	
	public void delete(Guestbook guestbook){
		guestbookRepository.delete(guestbook);
	}
}
