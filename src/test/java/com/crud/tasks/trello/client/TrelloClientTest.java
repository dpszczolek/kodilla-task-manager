package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

       @InjectMocks
       private TrelloClient trelloClient;

       @Mock
       private RestTemplate restTemplate;

       @Mock
       private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException{
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/dpszczolek/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldReturnEmptyCard() throws URISyntaxException{

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[0];
        URI uri = new URI("http://test.com/members/dpszczolek/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(0, fetchedTrelloBoards.size());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException{
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

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=card%20sample%20name&desc=card%20sample%20desc&pos=top&idList=id_list");

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        //Than
        assertEquals("1", newCard.getId());
        assertEquals("card sample name", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }


    @Test
    public void shouldReturnEmptyList() {
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Task task",
                "Test Description",
                "top",
                "test_id"
        );

        CreatedTrelloCardDto createdTrelloCardDto = trelloClient.createNewCard(trelloCardDto);

        assertEquals(null, createdTrelloCardDto);
    }
}
