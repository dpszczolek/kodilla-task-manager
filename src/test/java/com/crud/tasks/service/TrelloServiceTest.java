package com.crud.tasks.service;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Test
    public void shouldFetchTrelloBoards() {
        List<TrelloBoardDto> fetchedBoards = new ArrayList<>();
        fetchedBoards.add(new TrelloBoardDto("test_id", "test_board", new ArrayList<>()));

        when(trelloClient.getTrelloBoards()).thenReturn(fetchedBoards);

        assertEquals(1, fetchedBoards.size());
        assertEquals("test_id", fetchedBoards.get(0).getId());
        assertEquals("test_board", fetchedBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "card sample name",
                "card sample desc",
                "top",
                "id_list");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "card sample name",
                "http://test.com"
        );

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        assertEquals("1", createdTrelloCardDto.getId());
        assertEquals("card sample name", createdTrelloCardDto.getName());
        assertEquals("http://test.com", createdTrelloCardDto.getShortUrl());
    }
}
