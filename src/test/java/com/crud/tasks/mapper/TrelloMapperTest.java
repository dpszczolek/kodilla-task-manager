package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "test name Dto", new ArrayList<>()));
        trelloBoardDtoList.add(new TrelloBoardDto("2", "test name2 Dto", new ArrayList<>()));

        trelloMapper.mapToBoards(trelloBoardDtoList);

        Assert.assertEquals(2, trelloMapper.mapToBoards(trelloBoardDtoList).size());
        Assert.assertEquals("test name2 Dto", trelloMapper.mapToBoards(trelloBoardDtoList).get(1).getName());
    }

    @Test
    public void testMapToBoardsDto() {
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "test name", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("2", "test name2", new ArrayList<>()));

        trelloMapper.mapToBoardsDto(trelloBoards);

        Assert.assertEquals(2, trelloMapper.mapToBoardsDto(trelloBoards).size());
        Assert.assertEquals("test name", trelloMapper.mapToBoardsDto(trelloBoards).get(0).getName());
        Assert.assertEquals("test name2", trelloMapper.mapToBoardsDto(trelloBoards).get(1).getName());
    }

    @Test
    public void testMapToList() {
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "list name Dto", false));
        trelloListDto.add(new TrelloListDto("2", "list name2 Dto", true));

        trelloMapper.mapToList(trelloListDto);

        Assert.assertEquals(2, trelloMapper.mapToList(trelloListDto).size());
        Assert.assertEquals("list name Dto", trelloMapper.mapToList(trelloListDto).get(0).getName());
        Assert.assertEquals(true, trelloMapper.mapToList(trelloListDto).get(1).isClosed());
    }

    @Test
    public void testMapToListDto() {
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "list name", false));
        trelloLists.add(new TrelloList("2", "list name2", false));

        trelloMapper.mapToListDto(trelloLists);

        Assert.assertEquals(2, trelloMapper.mapToListDto(trelloLists).size());
        Assert.assertEquals("list name2", trelloMapper.mapToListDto(trelloLists).get(1).getName());
        Assert.assertEquals(false, trelloMapper.mapToListDto(trelloLists).get(0).isClosed());
    }

    @Test
    public void testMapToCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "card test name Dto",
                "card test desc Dto",
                "bottom",
                "test_id"
        );

        trelloMapper.mapToCard(trelloCardDto);

        Assert.assertEquals("card test name Dto", trelloMapper.mapToCard(trelloCardDto).getName());
        Assert.assertEquals("card test desc Dto", trelloMapper.mapToCard(trelloCardDto).getDescription());
        Assert.assertEquals("bottom", trelloMapper.mapToCard(trelloCardDto).getPos());
        Assert.assertEquals("test_id", trelloMapper.mapToCard(trelloCardDto).getListId());
    }

    @Test
    public void testMapToCardDto() {
        TrelloCard trelloCard = new TrelloCard("card test name", "card test desc", "top", "test_id_list");

        trelloMapper.mapToCardDto(trelloCard);

        Assert.assertEquals("card test name", trelloMapper.mapToCardDto(trelloCard).getName());
        Assert.assertEquals("card test desc", trelloMapper.mapToCardDto(trelloCard).getDescription());
        Assert.assertEquals("top", trelloMapper.mapToCardDto(trelloCard).getPos());
        Assert.assertEquals("test_id_list", trelloMapper.mapToCardDto(trelloCard).getListId());
    }
}
