package org.bachert.imageorganizer.process;

import org.bachert.imageorganizer.process.dto.ProcessStateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @PostMapping("/start")
    public void startProcess(@RequestParam String directory) {
        processService.process(directory);
    }

    @GetMapping("/state")
    public ProcessStateDTO getState() {
        return processService.getState();
    }
}
