import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;

/**
 * {@code IntFilter} is a subclass of {@link DocumentFilter} that restricts input to integer values
 * It overrides the {@code insertString} and {@code replace} methods to filter out non-integer input.
 */
public class IntFilter extends DocumentFilter {

    /**
     * Inserts a string into the document. If the string contains only digits, it is inserted; otherwise, it is ignored.
     *
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset  the offset into the document to insert the content &gt;= 0.
     *    All positions that track change at or after the given location
     *    will move.
     * @param string the string to insert
     * @param attr      the attributes to associate with the inserted
     *   content.  This may be null if there are no attributes.
     * @throws BadLocationException If the given insert position is not a valid position within the document.
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches("^?\\d*$")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    /**
     * Replaces a portion of the document. If the replacement text contains only digits, it replaces the current content; otherwise, the replacement is ignored.
     *
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset Location in Document
     * @param length Length of text to delete
     * @param text Text to insert, null indicates no text to insert
     * @param attrs AttributeSet indicating attributes of inserted text,
     *              null is legal.
     * @throws BadLocationException If the given insert position is not a valid position within the document.
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("^?\\d*$")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
