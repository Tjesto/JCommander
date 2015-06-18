using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Windows.Forms;

namespace iDrawer.Plugins
{
    public interface Base
    {
        string getName();
        Base getInstance();
    }

    public interface DrawingTool : Base
    {
        void draw(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width);
        void onDrawStarted(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width);
        void onDrawFinished(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width);
        ToolStripItem getItem();
        void registerOnToolChangedListener(OnToolChangedListener listener);
    }

    public interface BasePlugin : Base
    {
        ToolStripMenuItem createMenuItem();
    }

    public interface OnToolChangedListener
    {
        void notifyChanged(DrawingTool newTool);
    }
}
