package com.nb.futbol24checker.service;

import com.nb.futbol24checker.model.FutbolResponse;
import com.nb.futbol24checker.model.Match;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileWriterService {

    public void filterMatchesAndWriteToCsv(String dateAsStringWithNewFormat, FutbolResponse response) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(dateAsStringWithNewFormat + ".csv")) {
            pw.write("Home Team Name, Away Team Name, Game Date, Game Time");
            pw.println();
            transformFilteredMatchesToLines(filterMatches(response))
                    .stream()
                    .map(e -> String.join(",", e))
                    .forEach(pw::println);
        }
    }

    private List<String[]> transformFilteredMatchesToLines(List<Match> matches) {
        return matches
                .stream()
                .map(e -> {
                    ZonedDateTime zonedDateTimeOfGame = ZonedDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(e.getTimestamp())), ZoneId.systemDefault());
                    LocalDate date = LocalDate.ofInstant(zonedDateTimeOfGame.toInstant(), zonedDateTimeOfGame.getZone());
                    LocalTime time = LocalTime.ofInstant(zonedDateTimeOfGame.toInstant(), zonedDateTimeOfGame.getZone());
                    return new String[] {e.getHomeNameTeam(), e.getGuestNameTeam(), date.toString(), time.toString()};
                })
                .collect(Collectors.toList());
    }

    private List<Match> filterMatches(FutbolResponse response) {
        return response.getMatches().stream()
                .filter(m -> this.checkTeamName(m.getGuestNameTeam()) || this.checkTeamName(m.getHomeNameTeam()))
                .collect(Collectors.toList());
    }

    private boolean checkTeamName(String teamName) {
        List<String> words = Arrays.stream(teamName.split(" ")).toList();
        String lastWord = words.get(words.size() - 1);
        boolean containsAmat = words.stream().anyMatch(e -> e.startsWith("Amat"));
        boolean lastWordCondition = lastWord.equals("C") || lastWord.equals("B") || lastWord.equals("II") || lastWord.equals("III") || lastWord.equals("(R)") || lastWord.equals("Reserves") || lastWord.equals("2");
        boolean u21Condition = teamName.toLowerCase().contains("u 21".toLowerCase()) || teamName.toLowerCase().contains("u 23".toLowerCase());
        boolean u23Condition = teamName.toLowerCase().contains("u21".toLowerCase()) || teamName.toLowerCase().contains("u23".toLowerCase());
        return lastWordCondition || containsAmat || u21Condition || u23Condition;
    }

}
