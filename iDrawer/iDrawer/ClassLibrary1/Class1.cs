using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Windows.Forms;

namespace iDrawer.Plugins
{
    /// <summary>
    /// Base interface for all plugins and tools.
    /// Classes implementing just the Base interface won't be able to be used in application
    /// </summary>
    public interface Base
    {
        /// <summary>        
        /// </summary>
        /// <returns>Name of plugin or tool</returns>
        string getName();
        /// <summary>
        /// Best implementation for getInstance() should be just "return this;"
        /// </summary>
        /// <returns>Current instance of plugin/tool</returns>
        Base getInstance();
    }
    /// <summary>
    /// An interface defined to create tools. The tool is used for drawing in application
    /// All implementors of this interface placed in directory Tools of main app will be installed just after the application start
    /// </summary>
    public interface DrawingTool : Base
    {
        /// <summary>
        /// Method called by application when drawing by moving mouse cursor.
        /// </summary>
        /// <param name="p">System.Drawing.Pen used for drawing lines, edges or points</param>
        /// <param name="b">System.Drawing.Brush with the same color as Pen p, please use it just for creating new Pen with given width</param>
        /// <param name="g">System.Drawing.Graphics indicating where to draw</param>
        /// <param name="startX">int, value of X axis from the beggining of movement</param>
        /// <param name="startY">int, value of Y axis from the beggining of movement</param>
        /// <param name="x">int, value of X axis from current state of movement</param>
        /// <param name="y">int, value of Y axis from current state of movement</param>
        /// <param name="width">int, given width of line, used to draw</param>
        /// <param name="fill">System.Drawing.Brush used for filling 2D drawings</param>
        void draw(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width, Brush fill);
        /// <summary>
        /// Method called by application when the drawing action starts.
        /// </summary>
        /// <param name="p">System.Drawing.Pen used for drawing lines, edges or points</param>
        /// <param name="b">System.Drawing.Brush with the same color as Pen p, please use it just for creating new Pen with given width</param>
        /// <param name="g">System.Drawing.Graphics indicating where to draw</param>
        /// <param name="startX">int, value of X axis from the beggining of movement</param>
        /// <param name="startY">int, value of Y axis from the beggining of movement</param>
        /// <param name="x">int, value of X axis from current state of movement</param>
        /// <param name="y">int, value of Y axis from current state of movement</param>
        /// <param name="width">int, given width of line, used to draw</param>
        /// <param name="fill">System.Drawing.Brush used for filling 2D drawings</param>
        void onDrawStarted(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width, Brush fill);
        /// <summary>
        /// Method called by application when the drawing action ends.
        /// </summary>
        /// <param name="p">System.Drawing.Pen used for drawing lines, edges or points</param>
        /// <param name="b">System.Drawing.Brush with the same color as Pen p, please use it just for creating new Pen with given width</param>
        /// <param name="g">System.Drawing.Graphics indicating where to draw</param>
        /// <param name="startX">int, value of X axis from the beggining of movement</param>
        /// <param name="startY">int, value of Y axis from the beggining of movement</param>
        /// <param name="x">int, value of X axis from current state of movement</param>
        /// <param name="y">int, value of Y axis from current state of movement</param>
        /// <param name="width">int, given width of line, used to draw</param>
        /// <param name="fill">System.Drawing.Brush used for filling 2D drawings</param>
        void onDrawFinished(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width, Brush fill);
        /// <summary>
        /// Returns item to be put in application's toolbar. The item should contains it's name and handlers for every event is needed        
        /// </summary>
        /// <returns>A ToolStripItem to be placed in toolbar</returns>
        ToolStripItem getItem();
        /// <summary>
        /// Registering OnToolChangedListener. The listeners will be notified when the toolItem set by getItem was clicked. Main application window is also the listener for that event
        /// </summary>
        /// <param name="listener">OnToolChangedListener that needs to be notified when tool is changed or the state of tool has changed</param>
        void registerOnToolChangedListener(OnToolChangedListener listener);        
    }

    /// <summary>
    /// An interface defined to create plugins. The plugin can do everything.
    /// All implementors of this interface placed in directory Plugins of main app will be installed just after the application start
    /// </summary>
    public interface BasePlugin : Base
    {
        /// <summary>
        /// Sets DrawingWindow, reference to main application window, which allows to communicate
        /// </summary>
        /// <param name="window">The main application window</param>
        void setDrawingWindow(DrawingWindow window);
        /// <summary>
        /// Returns item to be put in application's Menu strip. The item should contains it's name and handlers for every event is needed        
        /// </summary>
        /// <returns>An ToolStripMenuItem to be placed in applications Menu</returns>
        ToolStripMenuItem createMenuItem();
    }
    /// <summary>
    /// Listener for tool changes. Use with DrawingTool only
    /// </summary>
    public interface OnToolChangedListener
    {
        /// <summary>
        /// Notifies the listeners that used tool has changed or its state has changed
        /// </summary>
        /// <param name="newTool"></param>
        void notifyChanged(DrawingTool newTool);
    }

    /// <summary>
    /// An interface represents an application window. DO NOT implement this interface, it's just a delegate for main application window
    /// </summary>
    public interface DrawingWindow
    {
        /// <summary>
        /// Updates operation progress in statusbar
        /// </summary>
        /// <param name="newValue">New value of progress</param>
        void updateProgress(int newValue);        
        /// <summary>
        /// Get current drawing as bitmap
        /// </summary>
        /// <returns>Bitmap containing current drawing</returns>
        Bitmap getCurrentBitmap();        
        /// <summary>
        /// Updates current DrawingPanel by setting a Bitmap
        /// </summary>
        /// <param name="newBitmap">Bitmap to be set as current drawing</param>
        void updateDrawingBoard(Bitmap newBitmap);        
        /// <summary>
        /// Prevents window from closing
        /// </summary>
        void lockClosing(); 
        /// <summary>
        /// Notifies window, that it can be closed at anytime
        /// </summary>
        void unlockClosing();        
        /// <summary>
        /// Set information about current application state. Use for indicating time-consuming operations
        /// </summary>
        /// <param name="justStarted">boolean value indicates if the operation started (true) or finished (false)</param>
        void setOperationInProgress(bool justStarted);
        /// <summary>
        /// Get color selected as main (or edge color)
        /// </summary>
        /// <returns>Main color</returns>
        Color getPrimaryColor();
        /// <summary>
        /// Get color selected as fill color
        /// </summary>
        /// <returns>Fill color</returns>
        Color getFillColor();
    }
}
