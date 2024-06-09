package site.doto.domain.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MembersSearchRes {
    List<MembersSearchDto> searchResult = new ArrayList<>();

    public MembersSearchRes(List<MembersSearchDto> members) {
        searchResult.addAll(members);
    }
}
