package bx.app.data.local

object CardQuery {
    const val CARD_WITH_SIDES_CONTENT = """
    SELECT 
        c.*,
        iif(c.front_side_type = "AUDIO", fa.file_name, ft.text) AS front_text,
        iif(c.back_side_type = "AUDIO", ba.file_name, bt.text) AS back_text,
        fa.path AS front_path,
        ba.path AS back_path,
        length(iif(c.front_side_type = "AUDIO", fa.file_name, ft.text)) AS front_text_length
    FROM card c
    LEFT JOIN text_side ft ON ft.card_id = c.id AND ft.id = c.front_side_id
    LEFT JOIN audio_side fa ON fa.card_id = c.id AND fa.id = c.front_side_id
    LEFT JOIN text_side bt ON bt.card_id = c.id AND bt.id = c.back_side_id
    LEFT JOIN audio_side ba ON ba.card_id = c.id AND ba.id = c.back_side_id
"""
}