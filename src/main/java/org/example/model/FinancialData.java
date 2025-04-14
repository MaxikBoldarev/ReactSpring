package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Value
@AllArgsConstructor
@Builder
public class FinancialData {
    String numCode;
    String charCode;
    String nominal;
    String name;
    String value;
}
