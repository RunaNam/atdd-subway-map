package wooteco.subway.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.subway.dao.LineDao;
import wooteco.subway.domain.Line;
import wooteco.subway.dto.LineRequest;
import wooteco.subway.dto.LineResponse;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class LineController {

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest lineRequest) {
        String name = lineRequest.getName();
        String color = lineRequest.getColor();

        Line newLine = LineDao.save(new Line(name, color));
        LineResponse lineResponse = new LineResponse(newLine.getId(), newLine.getName(), newLine.getColor());
        return ResponseEntity.created(URI.create("/lines/" + newLine.getId())).body(lineResponse);
    }

    @GetMapping(value = "/lines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LineResponse>> showLines() {
        List<Line> lines = LineDao.findAll();
        List<LineResponse> lineResponses = lines.stream()
                .map(it -> new LineResponse(it.getId(), it.getName(), it.getColor()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(lineResponses);
    }

    @GetMapping(value = "/lines/{id}")
    public ResponseEntity<LineResponse> showLine(@PathVariable Long id) {
        Line line = LineDao.findById(id);
        return ResponseEntity.ok().body(new LineResponse(line.getId(), line.getName(), line.getColor()));
    }
}
