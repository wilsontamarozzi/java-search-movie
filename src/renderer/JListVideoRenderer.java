package renderer;

import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Video;

public class JListVideoRenderer extends JLabel implements ListCellRenderer<Video> {
	
	private static final long serialVersionUID = 1L;

	protected static Border noFocusBorder = new EmptyBorder(15, 10, 10, 10);

	protected static TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder());

	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	public JListVideoRenderer() {
        setOpaque(true);
    }
	
	@Override
    public Component getListCellRendererComponent(JList<? extends Video> list, Video video, int index,
        boolean isSelected, boolean cellHasFocus) {		

		Image img = new ImageIcon(video.getThumbnailPath()).getImage();
		Image newimg = img.getScaledInstance(178, 100,  java.awt.Image.SCALE_SMOOTH);
		
		setIcon(new ImageIcon(newimg));
		setToolTipText(video.getNome());
		
		setBorder(cellHasFocus ? focusBorder : noFocusBorder);
		
		setHorizontalAlignment(JLabel.CENTER);
	    setVerticalAlignment(JLabel.CENTER);
		
        if(isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
         
        return this;
    }
}