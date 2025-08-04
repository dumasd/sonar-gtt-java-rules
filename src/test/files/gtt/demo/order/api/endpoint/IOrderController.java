/*
 * This file is part of Bitkernel GTT Java Rules.
 *
 * Bitkernel GTT Java Rules is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bitkernel GTT Java Rules is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package gtt.demo.order.api.endpoint;

import bikframework.web.domain.ApiResponse;
import gtt.videomart.drmlicense.api.model.dto.BatchDrmStreamPsshDataResponseDTO;
import gtt.videomart.drmlicense.api.model.dto.BatchDrmStreamQueryDTO;
import gtt.videomart.drmlicense.api.model.dto.DrmLicenseQueryRequestDTO;
import gtt.videomart.drmlicense.api.model.dto.DrmStreamLicenseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kevin
 */
@Tag(name = "App License Manage", description = "license api for front-end")
@RequestMapping("/api/demo/order")
public interface IOrderController {

    /**
     * Query Stream License.
     *
     * @param request request object
     * @return Response object
     */
    @Operation(summary = "Query Stream License", description = "Query Stream License")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = DrmStreamLicenseResponseDTO.class))})
    @PostMapping(value = "/stream-license/query/v1", consumes = {"application/json"}, produces = {"application/json"})
    ApiResponse<DrmStreamLicenseResponseDTO> queryStreamLicense(@RequestBody DrmLicenseQueryRequestDTO request, @RequestHeader("Authorization") String accessToken, HttpServletRequest servletRequest);

    /**
     * Query Stream Pssh.
     *
     * @param request request object
     * @return Response object
     */
    @Operation(summary = "Query Stream Pssh", description = "Query Stream Pssh,app query")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BatchDrmStreamPsshDataResponseDTO.class))})
    @PostMapping(value = "/stream-pssh/batch-query/v1", consumes = {"application/json"}, produces = {"application/json"})
    ApiResponse<BatchDrmStreamPsshDataResponseDTO> batchQueryStreamPssh(@RequestBody BatchDrmStreamQueryDTO request, @RequestHeader("Authorization") String accessToken);

}
