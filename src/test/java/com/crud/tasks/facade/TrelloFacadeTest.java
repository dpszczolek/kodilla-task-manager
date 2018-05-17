package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloService trelloService;
    @Spy
    private TrelloValidator trelloValidator;
    @Spy
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchEmptyList() {

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {

        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);

        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("test", trelloBoardDto.getName());

        trelloBoardDto.getLists().forEach(trelloListDto -> {
        assertEquals("1", trelloListDto.getId());
        assertEquals("test", trelloListDto.getName());
        assertEquals(false, trelloListDto.isClosed());
        });
        });
    }

    @Test
    public void shouldCreateCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "card sample name",
                "card sample desc",
                "top",
                "id_list");
        TrelloCard trelloCard = new TrelloCard(
                "card sample name",
                "card sample desc",
                "top",
                "id_list");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "card sample name",
                "http://test.com"
        );

        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        CreatedTrelloCardDto test = trelloFacade.createCard(trelloCardDto);


        assertEquals("card sample name", test.getName());


    }
}
