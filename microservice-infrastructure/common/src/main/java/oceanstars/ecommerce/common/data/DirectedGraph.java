package oceanstars.ecommerce.common.data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import oceanstars.ecommerce.common.exception.SystemException;
import org.springframework.util.CollectionUtils;

/**
 * 有向图数据结构
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:29 下午
 */
public class DirectedGraph<T> implements Iterable<T>, Serializable {

  @Serial
  private static final long serialVersionUID = -4619043672341081217L;

  /**
   * 有向图通过邻接表方法进行存储
   */
  private final Map<T, Set<T>> graph;

  /**
   * 构造函数
   */
  public DirectedGraph() {
    this.graph = new HashMap<>();
  }

  /**
   * 构造函数
   *
   * @param graph 有向图
   */
  public DirectedGraph(Map<T, Set<T>> graph) {
    this.graph = graph;
  }

  /**
   * 新增顶点到图中
   *
   * @param node 新增顶点数据
   * @return 新增操作是否成功
   */
  public Boolean addNode(T node) {

    if (this.graph.containsKey(node)) {
      return Boolean.FALSE;
    }

    // 新增顶点空输出边集合
    this.graph.put(node, new HashSet<>());

    return Boolean.TRUE;
  }

  /**
   * 新增两顶点之间的边
   *
   * @param start 起始顶点数据
   * @param dest  结束顶点数据
   */
  public void addEdge(T start, T dest) {

    if (!this.graph.containsKey(start)) {
      throw new SystemException("1");
    } else if (!this.graph.containsKey(dest)) {
      throw new SystemException("2");
    }

    // 带新增的边已存在
    if (Boolean.TRUE.equals(this.edgeExists(start, dest))) {
      return;
    }

    // 新增有向边
    this.graph.get(start).add(dest);
  }

  /**
   * 去除两顶点之间的边
   *
   * @param start 起始顶点数据
   * @param dest  结束顶点数据
   */
  public void removeEdge(T start, T dest) {

    if (!this.graph.containsKey(start)) {
      throw new SystemException("1");
    } else if (!this.graph.containsKey(dest)) {
      throw new SystemException("2");
    }

    // 去除有向边
    this.graph.get(start).remove(dest);
  }

  /**
   * 判定两顶点间是否存在有向边
   *
   * @param start 起始顶点数据
   * @param dest  结束顶点数据
   * @return 判定结果
   */
  public Boolean edgeExists(T start, T dest) {

    if (!this.graph.containsKey(start) || !this.graph.containsKey(dest)) {
      return Boolean.FALSE;
    }

    return this.graph.get(start).contains(dest);
  }

  /**
   * 获取顶点出发路径集合
   *
   * @param node 顶点数据
   * @return 顶点出发路径集合
   */
  public Set<T> edgesFrom(T node) {

    Set<T> arcs = this.graph.get(node);
    if (arcs == null) {
      throw new SystemException("");
    }

    return Collections.unmodifiableSet(arcs);
  }

  /**
   * 获取有向图信息
   *
   * @return 有向图信息
   */
  public Map<T, Set<T>> getGraph() {
    return this.graph;
  }

  @Override
  public Iterator<T> iterator() {
    return this.graph.keySet().iterator();
  }

  /**
   * 获取图中顶点数量
   *
   * @return 图中顶点数量
   */
  public int size() {
    return this.graph.size();
  }

  /**
   * 判定图是否为空
   *
   * @return 判定结果
   */
  public Boolean isEmpty() {
    return this.graph.isEmpty();
  }

  /**
   * 有向图反转
   *
   * @return 反转后有向图
   */
  public DirectedGraph<T> reverse() {

    // 初始化反转图对象
    final DirectedGraph<T> reverseGraph = new DirectedGraph<>();

    if (Boolean.TRUE.equals(this.isEmpty())) {
      return reverseGraph;
    }

    // 递归实现有向图反转
    this.graph.forEach((node, nodes) -> this.reverse(node, nodes, reverseGraph));

    return reverseGraph;
  }

  /**
   * 获取指定起始点的所在路径上的所有目标节点
   *
   * @param start 起始节点
   * @return 指定起始点的所在路径上的所有目标节点
   */
  public Set<T> getDestNodes(T start) {

    // DAG有向环检查
    final DirectedCycle<T> directedCycle = new DirectedCycle<>(this, start);

    if (directedCycle.hasCircle()) {
      // TODO: DAG存在环形依赖
      throw new SystemException("DAG存在环形依赖");
    }

    return directedCycle.markedNodes();
  }

  /**
   * 递归方法：反转有向图边方向
   *
   * @param node         原始图起始节点
   * @param nodes        原始图终结点列表
   * @param reverseGraph 待反转有向图
   */
  private void reverse(final T node, final Set<T> nodes, final DirectedGraph<T> reverseGraph) {

    reverseGraph.addNode(node);

    if (CollectionUtils.isEmpty(nodes)) {
      return;
    }
    nodes.forEach(item -> {
      // 节点存在，说明已经遍历过，新增边后，跳出递归
      if (Boolean.FALSE.equals(reverseGraph.addNode(item))) {
        this.reverse(item, this.getGraph().get(item), reverseGraph);
      }
      reverseGraph.addEdge(item, node);
    });
  }
}
